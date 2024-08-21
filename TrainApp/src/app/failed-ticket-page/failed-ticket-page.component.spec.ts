import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FailedTicketPageComponent } from './failed-ticket-page.component';

describe('FailedTicketPageComponent', () => {
  let component: FailedTicketPageComponent;
  let fixture: ComponentFixture<FailedTicketPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FailedTicketPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FailedTicketPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
