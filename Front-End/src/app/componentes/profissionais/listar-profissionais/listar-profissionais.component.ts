import { Component, OnInit } from '@angular/core';
import { ProfissionalService } from '../service/pessoa-fisica/profissional.service';
import { PessoaFisica } from '../interfaces/pessoa-fisica';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-listar-profissionais',
  templateUrl: './listar-profissionais.component.html',
  styleUrl: './listar-profissionais.component.css',
})
export class ListarProfissionaisComponent implements OnInit {
  listaProfissionais: PessoaFisica[] = [];
  filtro!: string;

  constructor(
    private service: ProfissionalService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.service.listar().subscribe((listaProfissionais) => {
      this.listaProfissionais = listaProfissionais.content;
    });
  }

  nomeCheck: boolean = true;
  cpfCheck: boolean = false;
  cargoCheck: boolean = false;
  idCheck: boolean = false;

  onCheckboxChange(checkbox: string) {
    switch (checkbox) {
      case 'nome':
        this.nomeCheck = true;
        this.cpfCheck = false;
        this.cargoCheck = false;
        this.idCheck = false;
        this.pesquisarProfissional();
        break;
      case 'cpf':
        this.nomeCheck = false;
        this.cpfCheck = true;
        this.cargoCheck = false;
        this.idCheck = false;
        this.pesquisarProfissional();
        break;
      case 'cargo':
        this.nomeCheck = false;
        this.cpfCheck = false;
        this.cargoCheck = true;
        this.idCheck = false;
        this.pesquisarProfissional();
        break;
      case 'id':
        this.nomeCheck = false;
        this.cpfCheck = false;
        this.cargoCheck = false;
        this.idCheck = true;
        this.pesquisarProfissional();
        break;
      default:
        break;
    }
  }

  pesquisarProfissional() {
    if (this.filtro.length > 0) {
      if (this.nomeCheck) {
        this.service.buscarPorNome(this.filtro).subscribe({
          next: (listaProfissionais) => {
            this.listaProfissionais = listaProfissionais.content;
          },
          error: (error: HttpErrorResponse) => {
            this.listaProfissionais = [];
          },
        });
      }else if (this.idCheck) {
        this.service.buscarPorId(parseInt(this.filtro)).subscribe({
          next: (listaProfissionais) => {
            this.listaProfissionais[0] = listaProfissionais;
          },
          error: (error: HttpErrorResponse) => {
            this.listaProfissionais = [];
          },
        });
      }else if (this.cpfCheck) {
        this.service.buscarPorCpf(this.filtro).subscribe({
          next: (listaProfissionais) => {
            this.listaProfissionais = listaProfissionais.content;
          },
          error: (error: HttpErrorResponse) => {
            this.listaProfissionais = [];
          },
        });
      }else if (this.cargoCheck) {
        this.service.buscarPorCargo(this.filtro).subscribe({
          next: (listaProfissionais) => {
            this.listaProfissionais = listaProfissionais.content;
          },
          error: (error: HttpErrorResponse) => {
            this.listaProfissionais = [];
          },
        });
      }
    } else {
      this.service.listar().subscribe((listaProfissionais) => {
        this.listaProfissionais = listaProfissionais.content;
      });
    }
  }
}
