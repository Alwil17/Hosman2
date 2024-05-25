import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReqPecPageComponent } from './req-pec-page.component';

describe('ReqPecPageComponent', () => {
  let component: ReqPecPageComponent;
  let fixture: ComponentFixture<ReqPecPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReqPecPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReqPecPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
