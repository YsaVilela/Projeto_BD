import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerMaisEmpresaComponent } from './ver-mais-empresa.component';

describe('VerMaisEmpresaComponent', () => {
  let component: VerMaisEmpresaComponent;
  let fixture: ComponentFixture<VerMaisEmpresaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VerMaisEmpresaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VerMaisEmpresaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
