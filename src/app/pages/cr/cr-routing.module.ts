import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CrBoardComponent } from './board/board.component';
import { CreateComponent } from './create/create.component';

const routes: Routes = [
  // { path: "",  redirectTo: "", pathMatch: "full", },
  { path: "", component: CrBoardComponent,  pathMatch: "full",  },
  { path: "create", component: CreateComponent,  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class CrRoutingModule { }
