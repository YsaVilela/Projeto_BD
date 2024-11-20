import { Component } from '@angular/core';
import { CargoService } from '../service/cargo/cargo.service';
import { Cargo } from '../interfaces/cargo';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-listar-cargo',
  templateUrl: './listar-cargo.component.html',
  styleUrl: './listar-cargo.component.css'
})
export class ListarCargoComponent {
  listaCargos: Cargo[] = [];
  filtro!: String;

  constructor(private service: CargoService) {}

  ngOnInit(): void {
      this.service.listar().subscribe((listaCargos) => {
      this.listaCargos = listaCargos.content;
    });
  }

  pesquisarCargo() {
    if (this.filtro.length > 0) {
      this.service.buscarPorNomeDinamico(this.filtro).subscribe({
        next: (listaCargos) => {
          this.listaCargos = listaCargos.content;
        },
        error: (error: HttpErrorResponse) => {
          this.listaCargos = [];
        },
      });
    }else{
      this.service.listar().subscribe((listaCargos) => {
        this.listaCargos = listaCargos.content;
      });
    }
  }
}
