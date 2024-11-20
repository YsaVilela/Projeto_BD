import { TestBed } from '@angular/core/testing';

import { DominioService } from './dominio.service';

describe('DominioService', () => {
  let service: DominioService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DominioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
