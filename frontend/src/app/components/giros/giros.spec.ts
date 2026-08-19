import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Giros } from './giros';

describe('Giros', () => {
  let component: Giros;
  let fixture: ComponentFixture<Giros>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Giros],
    }).compileComponents();

    fixture = TestBed.createComponent(Giros);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
