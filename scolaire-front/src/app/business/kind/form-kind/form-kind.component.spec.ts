import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormKindComponent } from './form-kind.component';

describe('FormKindComponent', () => {
  let component: FormKindComponent;
  let fixture: ComponentFixture<FormKindComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormKindComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormKindComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
