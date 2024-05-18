import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComptableTableClassicComponent } from './comptable-table-classic.component';

describe('ComptableTableClassicComponent', () => {
  let component: ComptableTableClassicComponent;
  let fixture: ComponentFixture<ComptableTableClassicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ComptableTableClassicComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ComptableTableClassicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
