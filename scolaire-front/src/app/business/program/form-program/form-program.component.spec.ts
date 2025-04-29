import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormProgramComponent } from './form-program.component';

describe('FormProgramComponent', () => {
  let component: FormProgramComponent;
  let fixture: ComponentFixture<FormProgramComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormProgramComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormProgramComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
