import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-comptable-table-classic',
  templateUrl: './comptable-table-classic.component.html',
  styleUrls: ['./comptable-table-classic.component.scss']
})
export class ComptableTableClassicComponent implements OnInit {

  @Input() typeData : string | null | undefined;
  @Input() tableData : any[] = []


  constructor() { }

  ngOnInit(): void {

  }

}
