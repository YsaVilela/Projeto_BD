import { Component, OnInit } from '@angular/core';
import { PessoaJuridica } from '../interfaces/pessoa-juridica';
import { EmpresaService } from '../service/pessoa-juridica/empresa.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-listar-empresas',
  templateUrl: './listar-empresas.component.html',
  styleUrl: './listar-empresas.component.css',
})
export class ListarEmpresasComponent implements OnInit {
  listaEmpresas: PessoaJuridica[] = [];
  filtro!: String;

  constructor(private service: EmpresaService) {}

  ngOnInit(): void {
    this.service.listar().subscribe((listaEmpresas) => {
      this.listaEmpresas = listaEmpresas.content;
    });
  }

  pesquisarEmpresa() {
    if (this.filtro.length > 0) {
      this.service.buscarPorNome(this.filtro).subscribe({
        next: (listaEmpresas) => {
          this.listaEmpresas = listaEmpresas.content;
        },
        error: (error: HttpErrorResponse) => {
          this.listaEmpresas = [];
        },
      });
    }else{
      this.service.listar().subscribe((listaEmpresas) => {
        this.listaEmpresas = listaEmpresas.content;
      });
    }
  }
}
