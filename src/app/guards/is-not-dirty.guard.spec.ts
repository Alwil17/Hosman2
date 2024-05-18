import { TestBed } from '@angular/core/testing';

import { IsNotDirtyGuard } from './is-not-dirty.guard';

describe('IsNotDirtyGuard', () => {
  let guard: IsNotDirtyGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(IsNotDirtyGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
