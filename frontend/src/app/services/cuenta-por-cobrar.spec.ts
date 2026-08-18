import { TestBed } from '@angular/core/testing';

import { CuentaPorCobrar } from './cuenta-por-cobrar';

describe('CuentaPorCobrar', () => {
  let service: CuentaPorCobrar;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CuentaPorCobrar);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
