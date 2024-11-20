import { Component, OnInit } from '@angular/core';
import { ProfissionalService } from '../service/pessoa-fisica/profissional.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PessoaFisica } from '../interfaces/pessoa-fisica';
import { DominioService } from '../service/dominio/dominio.service';
import { CargoService } from '../service/cargo/cargo.service';
import { switchMap } from 'rxjs/operators';
import { format } from 'date-fns';
import { Cargo } from '../interfaces/cargo';
import { HttpErrorResponse } from '@angular/common/http';
import { Estado } from '../interfaces/estado';
import { Cidade } from '../interfaces/cidade';

@Component({
  selector: 'app-editar-profissional',
  templateUrl: './editar-profissional.component.html',
  styleUrl: './editar-profissional.component.css',
})
export class EditarProfissionalComponent implements OnInit {
  constructor(
    private serviceProfissional: ProfissionalService,
    private serviceDominio: DominioService,
    private serviceCargo: CargoService,
    private router: Router,
    private route: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {}

  formularioPessoaFisica!: FormGroup;
  cargos: Cargo[] = [];
  estados: Estado[] = [];
  cidades: Cidade[] = [];
  pessoaNaoEncontrada!: String;

  ngOnInit(): void {
    this.serviceDominio.listarEstados().subscribe((estados) => {
      this.estados = estados.content;
    });

    this.serviceCargo.listar().subscribe((cargo) => {
      this.cargos = cargo.content;
    });

    this.formularioPessoaFisica = this.formBuilder.group({
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
            nome: [
              '',
              Validators.compose([
                Validators.required,
                Validators.pattern(/(.|\s)*\S(.|\s)*/),
              ]),
            ],
          }),
        }),
        status: true,
      }),
      cpf: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/),
        ]),
      ],
      cargo: this.formBuilder.group({
        id: null,
        nome: ['', Validators.required],
        remuneracao: 0,
      }),
    });

    const id = this.route.snapshot.paramMap.get('id');

    this.serviceProfissional.buscarPorId(parseInt(id!)).subscribe({
      next: (profissional) => {
        const dataFormatada = format(
          new Date(profissional.pessoa.dataDeConstituicao),
          'dd/MM/yyyy'
        );

        this.formularioPessoaFisica.setValue(profissional);

        this.formularioPessoaFisica
          .get('pessoa.dataDeConstituicao')
          ?.setValue(dataFormatada);

        this.serviceDominio
          .buscarCidadePorEstado(profissional.pessoa.endereco.cidade.estado.uf)
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
    this.formularioPessoaFisica
      .get('endereco.cidade')
      ?.setValue(
        this.cidades.find(
          (cidade) =>
            cidade.nome ==
            this.formularioPessoaFisica.get('pessoa.endereco.cidade.nome')
              ?.value
        )
      );

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
        .atualizar(this.formularioPessoaFisica.value)
        .subscribe({
          next: () => {
            this.router.navigate(['/listarEmpregados']);
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
        this.formularioPessoaFisica.get('pessoa.endereco.cidade.estado.uf')
          ?.value
      )
      .subscribe((cidades) => {
        this.cidades = cidades.content;
      });
  }
}
