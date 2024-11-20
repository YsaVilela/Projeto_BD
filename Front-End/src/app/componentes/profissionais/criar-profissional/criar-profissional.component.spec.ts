import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CriarProfissionalComponent } from './criar-profissional.component';

describe('CriarProfissionalComponent', () => {
  let component: CriarProfissionalComponent;
  let fixture: ComponentFixture<CriarProfissionalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CriarProfissionalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CriarProfissionalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
