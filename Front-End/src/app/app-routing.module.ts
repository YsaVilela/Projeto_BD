import { VerMaisProfissionalComponent } from './componentes/profissionais/ver-mais-profissional/ver-mais-profissional.component';
import { CriarProfissionalComponent } from './componentes/profissionais/criar-profissional/criar-profissional.component';
import { NgModule } from '@angular/core';
import { RouteReuseStrategy, RouterModule, Routes } from '@angular/router';
import { TelaInicioComponent } from './componentes/profissionais/tela-inicio/tela-inicio.component';
import { CriarCargoComponent } from './componentes/profissionais/criar-cargo/criar-cargo.component';
import { ListarProfissionaisComponent } from './componentes/profissionais/listar-profissionais/listar-profissionais.component';
import { ListarEmpresasComponent } from './componentes/profissionais/listar-empresas/listar-empresas.component';
import { EditarProfissionalComponent } from './componentes/profissionais/editar-profissional/editar-profissional.component';
import { EditarEmpresaComponent } from './componentes/profissionais/editar-empresa/editar-empresa.component';
import { CustomReuseStrategy } from './custom-reuse-estrategy';
import { ListarCargoComponent } from './componentes/profissionais/listar-cargos/listar-cargo.component';
import { EditarCargoComponent } from './componentes/profissionais/editar-cargo/editar-cargo.component';
import { VerMaisEmpresaComponent } from './componentes/profissionais/ver-mais-empresa/ver-mais-empresa.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    component: TelaInicioComponent
  },
  {
    path: 'cadastrarProfissional',
    component: CriarProfissionalComponent
  },
  {
    path: 'cadastrarCargo',
    component: CriarCargoComponent
  },
  {
    path: 'listarEmpregados',
    component: ListarProfissionaisComponent,
    data: {
      reuseComponent: true
    }
  },
  {
    path: 'listarEmpresas',
    component: ListarEmpresasComponent,
    data: {
      reuseComponent: true
    }
  },
  {
    path: 'listarCargos',
    component: ListarCargoComponent,
    data: {
      reuseComponent: true
    }
  },
  {
    path: 'atualizarProfissional/:id',
    component: EditarProfissionalComponent
  },
  {
    path: 'atualizarEmpresa/:id',
    component: EditarEmpresaComponent
  },
  {
    path: 'atualizarCargo/:id',
    component: EditarCargoComponent
  },
  {
    path: 'dadosProfissional/:id',
    component: VerMaisProfissionalComponent
  },
  {
    path: 'dadosEmpresa/:id',
    component: VerMaisEmpresaComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule],
  providers: [
    {provide: RouteReuseStrategy, useClass: CustomReuseStrategy}
  ],
 })
export class AppRoutingModule { }
