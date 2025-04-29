import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListHalfYearlyComponent } from './list-half-yearly.component';

describe('ListHalfYearlyComponent', () => {
  let component: ListHalfYearlyComponent;
  let fixture: ComponentFixture<ListHalfYearlyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListHalfYearlyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListHalfYearlyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
