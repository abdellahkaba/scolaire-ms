import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormHalfYearlyComponent } from './form-half-yearly.component';

describe('FormHalfYearlyComponent', () => {
  let component: FormHalfYearlyComponent;
  let fixture: ComponentFixture<FormHalfYearlyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormHalfYearlyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormHalfYearlyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
