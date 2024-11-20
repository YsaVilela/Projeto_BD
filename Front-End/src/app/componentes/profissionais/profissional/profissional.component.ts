import { Component, Input } from '@angular/core';
import { PessoaFisica } from '../interfaces/pessoa-fisica';
import { ProfissionalService } from '../service/pessoa-fisica/profissional.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profissional',
  templateUrl: './profissional.component.html',
  styleUrl: './profissional.component.css',
})
export class ProfissionalComponent {
  @Input() profissional: PessoaFisica = {
    id: 0,
    cpf: '',
    pessoa: {
      id: 0,
      nome: '',
      dataDeConstituicao: '',
      email: '',
      telefone: '',
      endereco: {
        id: 0,
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
      status: true,
    },
    cargo: {
      id: 0,
      nome: '',
      remuneracao: 0,
    },
  };

  constructor(
    private serviceProfissional: ProfissionalService,
    private router: Router
  ) {}

  excluir() {
    if (this.profissional.id) {
      this.serviceProfissional.excluir(this.profissional.id).subscribe(() => {
        this.router.navigate(['/listarEmpregados']);
      });
    }
  }
}


