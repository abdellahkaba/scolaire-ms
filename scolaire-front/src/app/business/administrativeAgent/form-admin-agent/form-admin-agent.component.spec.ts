import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormAdminAgentComponent } from './form-admin-agent.component';

describe('FormAdminAgentComponent', () => {
  let component: FormAdminAgentComponent;
  let fixture: ComponentFixture<FormAdminAgentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormAdminAgentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormAdminAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
