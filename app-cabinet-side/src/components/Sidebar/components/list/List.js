import { NavLink, useLocation } from "react-router-dom";
import cl from "./List.module.scss";
import st from "classnames";
import { Icon } from "../../../Elements/Icon/Icon";

const arr = [
  { id: 1, title: "Asosiy sahifa", to: "/" },
  { id: 2, title: "Dars jadvali", to: "/course" },
  { id: 3, title: "Imtihon", to: "/exam" },
  { id: 4, title: "Sertifikat & Diplom", to: "/certificate" },
  { id: 5, title: "Rezyume", to: "/resume" },
];

export const List = ({ onToggleOpen }) => {
  const { pathname } = useLocation();

  return (
    <ul>
      {arr.map((i) => (
        <li key={i.id}>
          <NavLink
            to={i.to}
            className={st(cl.list__item)}
            activeClassName={st(cl.list__item__active)}
            onClick={onToggleOpen}
          >
            <Icon
              icon="icon-notebook"
              size="sm"
              color={pathname === i.to && "green"}
            />
            <div>{i.title}</div>
          </NavLink>
        </li>
      ))}
      <br />
      <div className={st(cl.list__footer)}>
        <li>
          <NavLink
            to={"/rules"}
            className={st(cl.list__item)}
            activeClassName={st(cl.list__item__active)}
          >
            <Icon icon="icon-rules" size="sm" />
            <div>Qoidalar</div>
          </NavLink>
        </li>
        <li>
          <NavLink
            to={"/feauture"}
            className={st(cl.list__item)}
            activeClassName={st(cl.list__item__active)}
          >
            <Icon icon="icon-settings" size="sm" />
            <div>Tizim imkoniyatlari</div>{" "}
          </NavLink>
        </li>
      </div>
    </ul>
  );
};
