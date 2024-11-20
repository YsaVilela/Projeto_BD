import { Component, OnInit } from '@angular/core';
import { PessoaFisica } from '../interfaces/pessoa-fisica';
import { ProfissionalService } from '../service/pessoa-fisica/profissional.service';
import { ActivatedRoute, Router } from '@angular/router';
import { format } from 'date-fns';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-ver-mais-profissional',
  templateUrl: './ver-mais-profissional.component.html',
  styleUrl: './ver-mais-profissional.component.css',
})
export class VerMaisProfissionalComponent implements OnInit {
  constructor(
    private serviceProfissional: ProfissionalService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  profissional: PessoaFisica = {
    id: 0,
    cpf: '',
    pessoa: {
      nome: '',
      dataDeConstituicao: '',
      email: '',
      telefone: '',
      endereco: {
        cep: '',
        logradouro: '',
        numero: 0,
        complemento: '',
        cidade: {
          id: 0,
          nome: '',
          estado: {
            id: 0,
            nome: '',
            uf: '',
            regiao: '',
          },
        },
      },
    },
    cargo: {
      id: 0,
      nome: '',
      remuneracao: 0,
    },
  };

  mensagemDeErro!: String;

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    this.serviceProfissional.buscarPorId(parseInt(id!)).subscribe({
      next: (profissional) => {
        this.profissional = profissional;
      },
      error: (error: HttpErrorResponse) => {
        this.mensagemDeErro = error.error[0].mensagem;
      },
    });
  }

  desativar() {
    if (this.profissional.id) {
      this.serviceProfissional
        .atulizarStatus(this.profissional.id)
        .subscribe(() => {
          this.router.navigate(['/listarEmpregados']);
        });
    }
  }

  excluir() {
    if (this.profissional.id) {
      this.serviceProfissional.excluir(this.profissional.id).subscribe(() => {
        this.router.navigate(['/listarEmpregados']);
      });
    }
  }
}
