import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListKindsComponent } from './list-kinds.component';

describe('ListKindsComponent', () => {
  let component: ListKindsComponent;
  let fixture: ComponentFixture<ListKindsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListKindsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListKindsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
