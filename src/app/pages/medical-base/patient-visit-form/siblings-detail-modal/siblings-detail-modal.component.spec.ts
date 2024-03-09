import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SiblingsDetailModalComponent } from './siblings-detail-modal.component';

describe('SiblingsDetailModalComponent', () => {
  let component: SiblingsDetailModalComponent;
  let fixture: ComponentFixture<SiblingsDetailModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SiblingsDetailModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SiblingsDetailModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
