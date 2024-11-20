import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerMaisProfissionalComponent } from './ver-mais-profissional.component';

describe('VerMaisProfissionalComponent', () => {
  let component: VerMaisProfissionalComponent;
  let fixture: ComponentFixture<VerMaisProfissionalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VerMaisProfissionalComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(VerMaisProfissionalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
