import { Component, OnInit } from '@angular/core';
import { CargoService } from '../service/cargo/cargo.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-editar-cargo',
  templateUrl: './editar-cargo.component.html',
  styleUrl: './editar-cargo.component.css'
})
export class EditarCargoComponent implements OnInit {
  constructor(
    private cargoService: CargoService,
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
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

    const id = this.route.snapshot.paramMap.get('id');

    this.cargoService.buscarPorId(parseInt(id!)).subscribe((cargo) => {
      this.formularioCargo.setValue(cargo);
    })
  }

  mensagemDeErro!: String;

  cadastrar() {
    if (this.formularioCargo.valid) {
      this.cargoService.atualizar(this.formularioCargo.value).subscribe({
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
