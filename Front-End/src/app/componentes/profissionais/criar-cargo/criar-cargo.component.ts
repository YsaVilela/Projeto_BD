import { Cargo } from './../interfaces/cargo';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CargoService } from '../service/cargo/cargo.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-criar-cargo',
  templateUrl: './criar-cargo.component.html',
  styleUrl: './criar-cargo.component.css',
})
export class CriarCargoComponent implements OnInit {
  constructor(
    private cargoService: CargoService,
    private formBuilder: FormBuilder,
    private router: Router,
  ) {}

  formularioCargo!: FormGroup;

  ngOnInit(): void {
    this.formularioCargo = this.formBuilder.group({
      id: 0,
      nome: ['', Validators.compose([Validators.required])],
      remuneracao: [
        null,
        Validators.compose([
          Validators.required,
          Validators.pattern(/^[0-9]+(\.[0-9]{1,2})?$/),
        ]),
      ],
    });
  }

  mensagemDeErro!: String;

  cadastrar() {
    if (this.formularioCargo.valid) {
      this.cargoService.cadastrar(this.formularioCargo.value).subscribe({
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
