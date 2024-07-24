import { MenuItem } from "./menu.model";

export const MENU: MenuItem[] = [
  {
    id: 1,
    label: "CR.title",
    isTitle: true,
  },
  {
    id: 2,
    label: "CR.home",
    link: "/apps",
    icon: "bx bx-home fs-24",
  },
  {
    id: 2,
    label: "CR.dashboard",
    link: "/cr/",
    icon: "ri-dashboard-2-line",
  },
  {
    id: 2,
    label: "CR.create.title",
    link: "#",
    icon: "ri-file-list-line",
    subItems : [
      {
        id: 3,
        label: "CR.create.nopatient",
        link: "/cr/create",
        icon: "ri-file-list-line",
      },
      {
        id: 3,
        label: "CR.create.patient",
        link: "/cr/create",
        icon: "ri-file-list-line",
      },
    ]
  },
]