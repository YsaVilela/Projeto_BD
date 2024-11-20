import { Component, OnInit } from '@angular/core';
import { PessoaJuridica } from '../interfaces/pessoa-juridica';
import { EmpresaService } from '../service/pessoa-juridica/empresa.service';
import { DominioService } from '../service/dominio/dominio.service';
import { ActivatedRoute, Router } from '@angular/router';
import { format } from 'date-fns';
import { switchMap } from 'rxjs/operators';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Estado } from '../interfaces/estado';
import { Cidade } from '../interfaces/cidade';

@Component({
  selector: 'app-editar-empresa',
  templateUrl: './editar-empresa.component.html',
  styleUrl: './editar-empresa.component.css',
})
export class EditarEmpresaComponent implements OnInit {
  constructor(
    private serviceEmpresa: EmpresaService,
    private serviceDominio: DominioService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {}

  formularioPessoaJuridica!: FormGroup;
  estados: Estado[] = [];
  cidades: Cidade[] = [];
  pessoaNaoEncontrada!: String;

  ngOnInit(): void {
    this.serviceDominio.listarEstados().subscribe((estados) => {
      this.estados = estados.content;
    });

    this.formularioPessoaJuridica = this.formBuilder.group({
      id: null,
      pessoa: this.formBuilder.group({
        id: null,
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
          id: null,
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
            nome: ['', Validators.compose([Validators.required])],
          }),
        }),
        status: true,
      }),
      cnpj: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern(/^\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}$/),
        ]),
      ],
    });

    const id = this.route.snapshot.paramMap.get('id');

    this.serviceEmpresa.buscarPorId(parseInt(id!)).subscribe({
      next: (empresa) => {
        const dataFormatada = format(
          new Date(empresa.pessoa.dataDeConstituicao),
          'dd/MM/yyyy'
        );

        this.formularioPessoaJuridica.setValue(empresa);

        this.formularioPessoaJuridica
          .get('pessoa.dataDeConstituicao')
          ?.setValue(dataFormatada);

        this.serviceDominio
          .buscarCidadePorEstado(empresa.pessoa.endereco.cidade.estado.uf)
          .subscribe((cidades) => {
            this.cidades = cidades.content;
          });
      },
      error: (error: HttpErrorResponse) => {
        this.pessoaNaoEncontrada = error.error[0].mensagem;
      },
    });
  }

  mensagemDeErro!: String;

  cadastrar() {
    this.formularioPessoaJuridica
      .get('pessoa.endereco.cidade')
      ?.setValue(
        this.cidades.find(
          (cidade) =>
            cidade.nome ==
            this.formularioPessoaJuridica.get('pessoa.endereco.cidade.nome')
              ?.value
        )
      );

    if (this.formularioPessoaJuridica.valid) {
      this.serviceEmpresa
        .atualizar(this.formularioPessoaJuridica.value)
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

  pesquisarCidades() {
    this.serviceDominio
      .buscarCidadePorEstado(
        this.formularioPessoaJuridica.get('pessoa.endereco.cidade.estado.uf')
          ?.value
      )
      .subscribe((cidades) => {
        this.cidades = cidades.content;
      });
  }
}
