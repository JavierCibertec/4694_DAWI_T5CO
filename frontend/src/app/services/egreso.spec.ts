import { TestBed } from '@angular/core/testing';

import { Egreso } from './egreso';

describe('Egreso', () => {
  let service: Egreso;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Egreso);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
