import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeadTimeComponent } from './lead-time.component';

describe('LeadTimeComponent', () => {
  let component: LeadTimeComponent;
  let fixture: ComponentFixture<LeadTimeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LeadTimeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeadTimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
