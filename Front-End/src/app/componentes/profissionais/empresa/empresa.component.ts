import { Component, Input } from '@angular/core';
import { PessoaJuridica } from '../interfaces/pessoa-juridica';
import { EmpresaService } from '../service/pessoa-juridica/empresa.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-empresa',
  templateUrl: './empresa.component.html',
  styleUrl: './empresa.component.css'
})
export class EmpresaComponent {
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

  constructor(
    private serviceEmpresa: EmpresaService,
    private router: Router
  ) {}

  excluir(){
      this.serviceEmpresa.excluir(this.empresa.id).subscribe(()=>{
        this.router.navigate(["/listarEmpresas"])
      });
  }
}
