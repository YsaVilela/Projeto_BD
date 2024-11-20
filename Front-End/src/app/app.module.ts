import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { CabecalhoComponent } from './componentes/cabecalho/cabecalho.component';
import { RodapeComponent } from './componentes/rodape/rodape.component';
import { TelaInicioComponent } from './componentes/profissionais/tela-inicio/tela-inicio.component';
import { CriarProfissionalComponent } from './componentes/profissionais/criar-profissional/criar-profissional.component';
import { CriarCargoComponent } from './componentes/profissionais/criar-cargo/criar-cargo.component';
import { ListarProfissionaisComponent } from './componentes/profissionais/listar-profissionais/listar-profissionais.component';
import { ListarEmpresasComponent } from './componentes/profissionais/listar-empresas/listar-empresas.component';
import { ProfissionalComponent } from './componentes/profissionais/profissional/profissional.component';
import { EmpresaComponent } from './componentes/profissionais/empresa/empresa.component';
import { EditarProfissionalComponent } from './componentes/profissionais/editar-profissional/editar-profissional.component';
import { EditarEmpresaComponent } from './componentes/profissionais/editar-empresa/editar-empresa.component';
import { ListarCargoComponent } from './componentes/profissionais/listar-cargos/listar-cargo.component';
import { EditarCargoComponent } from './componentes/profissionais/editar-cargo/editar-cargo.component';
import { CargoComponent } from './componentes/profissionais/cargo/cargo.component';
import { VerMaisProfissionalComponent } from './componentes/profissionais/ver-mais-profissional/ver-mais-profissional.component';
import { VerMaisEmpresaComponent } from './componentes/profissionais/ver-mais-empresa/ver-mais-empresa.component';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
    AppComponent,
    CabecalhoComponent,
    RodapeComponent,
    TelaInicioComponent,
    CriarProfissionalComponent,
    CriarCargoComponent,
    ListarProfissionaisComponent,
    ListarEmpresasComponent,
    ProfissionalComponent,
    EmpresaComponent,
    EditarProfissionalComponent,
    EditarEmpresaComponent,
    ListarCargoComponent,
    EditarCargoComponent,
    CargoComponent,
    VerMaisProfissionalComponent,
    VerMaisEmpresaComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
