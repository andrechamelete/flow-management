import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CfdComponent } from './cfd.component';

describe('CfdComponent', () => {
  let component: CfdComponent;
  let fixture: ComponentFixture<CfdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CfdComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CfdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
