import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormSectorComponent } from './form-sector.component';

describe('FormSectorComponent', () => {
  let component: FormSectorComponent;
  let fixture: ComponentFixture<FormSectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormSectorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormSectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
