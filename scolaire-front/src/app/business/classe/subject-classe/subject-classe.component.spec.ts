import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubjectClasseComponent } from './subject-classe.component';

describe('SubjectClasseComponent', () => {
  let component: SubjectClasseComponent;
  let fixture: ComponentFixture<SubjectClasseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubjectClasseComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubjectClasseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
