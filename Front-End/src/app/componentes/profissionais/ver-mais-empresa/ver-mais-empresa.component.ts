import { EmpresaService } from './../service/pessoa-juridica/empresa.service';
import { Component, Input, OnInit } from '@angular/core';
import { PessoaJuridica } from '../interfaces/pessoa-juridica';
import { ActivatedRoute, Router } from '@angular/router';
import { format } from 'date-fns';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-ver-mais-empresa',
  templateUrl: './ver-mais-empresa.component.html',
  styleUrl: './ver-mais-empresa.component.css'
})

export class VerMaisEmpresaComponent implements OnInit {
  constructor(
    private serviceEmpresa: EmpresaService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  @Input() empresa: PessoaJuridica = {
    id: 0,
    cnpj: "",
    pessoa: {
      id: 0,
      nome: "",
      dataDeConstituicao: "",
      email: "",
      telefone: "",
      endereco: {
        id: 0,
        cep: "",
        logradouro: "",
        numero: 0,
        complemento: "",
        cidade: {
          id: 0,
          nome: "",
          estado: {
            id: 0,
            nome: "",
            uf: "",
            regiao: ""
          }
        }
      },
      status: true
    },
  };
  mensagemDeErro!: String;

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    this.serviceEmpresa
      .buscarPorId(parseInt(id!))
      .subscribe({
      next: (empresa) => {
        this.empresa = empresa;
      },
      error: (error: HttpErrorResponse) => {
        this.mensagemDeErro = error.error[0].mensagem;
      },
    });
  }

  desativar() {
    if (this.empresa.id) {
      this.serviceEmpresa.atulizarStatus(this.empresa.id).subscribe(() => {
        this.router.navigate(['/listarEmpresas']);
      });
    }
  }

  excluir() {
    if (this.empresa.id) {
      this.serviceEmpresa.excluir(this.empresa.id).subscribe(() => {
        this.router.navigate(['/listarEmpresas']);
      });
    }
  }
}
