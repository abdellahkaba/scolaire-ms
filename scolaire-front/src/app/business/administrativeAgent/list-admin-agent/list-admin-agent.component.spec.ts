import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAdminAgentComponent } from './list-admin-agent.component';

describe('ListAdminAgentComponent', () => {
  let component: ListAdminAgentComponent;
  let fixture: ComponentFixture<ListAdminAgentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListAdminAgentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListAdminAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
