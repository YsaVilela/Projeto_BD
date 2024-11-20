import { Endereco } from './../interfaces/endereco';
import { Component, OnInit } from '@angular/core';
import { ProfissionalService } from '../service/pessoa-fisica/profissional.service';
import { EmpresaService } from '../service/pessoa-juridica/empresa.service';
import { Router } from '@angular/router';
import { DominioService } from '../service/dominio/dominio.service';
import { CargoService } from '../service/cargo/cargo.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Estado } from '../interfaces/estado';
import { Cidade } from '../interfaces/cidade';
import { Cargo } from '../interfaces/cargo';

@Component({
  selector: 'app-criar-profissional',
  templateUrl: './criar-profissional.component.html',
  styleUrl: './criar-profissional.component.css',
})
export class CriarProfissionalComponent implements OnInit {
  constructor(
    private serviceProfissional: ProfissionalService,
    private serviceEmpresa: EmpresaService,
    private serviceDominio: DominioService,
    private serviceCargo: CargoService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  formularioPessoa!: FormGroup;
  formularioPessoaJuridica!: FormGroup;
  formularioPessoaFisica!: FormGroup;
  formularioEndereco!: FormGroup;
  formularioCidade!: FormGroup;
  formularioEstado!: FormGroup;
  estados: Estado[] = [];
  cidades: Cidade[] = [];
  cargos: Cargo[] = [];

  ngOnInit(): void {
    this.serviceDominio.listarEstados().subscribe((estados) => {
      this.estados = estados.content;
    });

    this.serviceCargo.listar().subscribe((cargo) => {
      this.cargos = cargo.content;
    });

    this.formularioPessoa = this.formBuilder.group({
      nome: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern(/^[a-zA-Z\s]*$/),
        ]),
      ],
      dataDeConstituicao: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern(/^([0-2]\d|3[01])\/(0\d|1[0-2])\/\d{4}$/),
        ]),
      ],
      email: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern(/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/),
        ]),
      ],
      telefone: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern(/^\(\d{2}\)\d{5}-\d{4}$/)
        ]),
      ],
      endereco: this.formBuilder.group({
        cep: [
          '',
          Validators.compose([
            Validators.required,
            Validators.pattern(/^\d{5}-\d{3}$/)
          ]),
        ],
        logradouro: ['', Validators.compose([Validators.required])],
        numero: [null, Validators.compose([Validators.required])],
        complemento: [''],
        cidade: this.formBuilder.group({
          estado: this.formBuilder.group({
            id: 0,
            nome: '',
            uf: ['', Validators.compose([Validators.required])],
            regiao: '',
          }),
          id: 0,
          nome: [
            '',
            Validators.compose([
              Validators.required,
              Validators.pattern(/(.|\s)*\S(.|\s)*/),
            ]),
          ],
        }),
      }),
    });

    this.formularioPessoaFisica = this.formBuilder.group({
      pessoa: this.formularioPessoa,
      cpf: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/),
        ]),
      ],
      cargo: this.formBuilder.group({
        id: 0,
        nome: ['', Validators.required],
        remuneracao: 0,
      }),
    });

    this.formularioPessoaJuridica = this.formBuilder.group({
      cnpj: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern(/^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$/),
        ]),
      ],
      pessoa: this.formularioPessoa,
    });
  }

  pessoaFisicaCheck: boolean = false;
  pessoaJuridicaCheck: boolean = false;
  mensagemDeErro!: String;

  onCheckboxChange(checkbox: string) {
    if (checkbox === 'pessoaFisica') {
      this.pessoaJuridicaCheck = false;
    } else if (checkbox === 'pessoaJuridica') {
      this.pessoaFisicaCheck = false;
    }
  }

  cadastrar() {
    this.formularioPessoa
      .get('endereco.cidade')
      ?.setValue(
        this.cidades.find(
          (cidade) =>
            cidade.nome ==
            this.formularioPessoa.get('endereco.cidade.nome')?.value
        )
      );
    if (this.pessoaFisicaCheck) {
      this.formularioPessoaFisica
      .get('cargo')
      ?.setValue(
        this.cargos.find(
          (cargo) =>
            cargo.nome == this.formularioPessoaFisica.get('cargo.nome')?.value
        )
      );
      if (this.formularioPessoaFisica.valid) {
        this.serviceProfissional
          .cadastrar(this.formularioPessoaFisica.value)
          .subscribe({
            next: () => {
              this.router.navigate(['/listarEmpregados']);
            },
            error: (error: HttpErrorResponse) => {
              this.mensagemDeErro = error.error[0].mensagem;
            },
          });
      }
    } else {
      if (this.formularioPessoaJuridica.valid) {
        this.serviceEmpresa
          .cadastrar(this.formularioPessoaJuridica.value)
          .subscribe({
            next: () => {
              this.router.navigate(['/listarEmpresas']);
            },
            error: (error: HttpErrorResponse) => {
              this.mensagemDeErro = error.error[0].mensagem;
            },
          });
      }
    }
  }

  pesquisarCidades() {
    this.serviceDominio
      .buscarCidadePorEstado(
        this.formularioPessoa.get('endereco.cidade.estado.uf')?.value
      )
      .subscribe((cidades) => {
        this.cidades = cidades.content;
      });
  }
}
