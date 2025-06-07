import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyPermissionComponent } from './company-permission.component';

describe('CompanyPermissionComponent', () => {
  let component: CompanyPermissionComponent;
  let fixture: ComponentFixture<CompanyPermissionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CompanyPermissionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompanyPermissionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
