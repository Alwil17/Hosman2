import { MenuItem } from "./menu.model";

export const MENU: MenuItem[] = [
  {
    id: 1,
    label: "MENUITEMS.hosp.title",
    isTitle: true,
  },
  {
    id: 2,
    label: "MENUITEMS.hosp.list",
    link: "/hospitalisation/list",
    icon: "ri-file-list-line",
  },
  // {
  //   id: 2,
  //   label: "MENUITEMS.hosp.admin",
  //   link: "/hospitalisation/administration",
  //   icon: "ri-list",
  // },
]