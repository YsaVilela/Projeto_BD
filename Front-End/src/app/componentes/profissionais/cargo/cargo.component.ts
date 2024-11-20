import { Component, Input } from '@angular/core';
import { Cargo } from '../interfaces/cargo';
import { Router } from '@angular/router';
import { CargoService } from '../service/cargo/cargo.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-cargo',
  templateUrl: './cargo.component.html',
  styleUrl: './cargo.component.css',
})
export class CargoComponent {
  @Input() cargo: Cargo = {
    id: 0,
    nome: 'Dev',
    remuneracao: 2000.0,
  };

  constructor(private serviceCargo: CargoService, private router: Router) {}

  mensagemDeErro!: String;

  excluir() {
    if (this.cargo.id) {
      this.serviceCargo.excluir(this.cargo.id).subscribe({
        next: () => {
          this.router.navigate(['/listarCargos']);
        },
        error: (error: HttpErrorResponse) => {
          this.mensagemDeErro = error.error[0].mensagem;
        },
      });
    }
  }
}
