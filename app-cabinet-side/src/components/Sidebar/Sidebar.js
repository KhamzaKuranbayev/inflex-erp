import { Link } from "react-router-dom";
import logoPDP from "../../assets/images/logo.png";
import { Card } from "./components/card/Card";
import { List } from "./components/list/List";
import cl from "./Sidebar.module.scss";
import st from "classnames";

export const Sidebar = ({onToggleOpen}) => {
  return (
    <nav
      className={st(cl.sidebar__cabinet)}
    >
      <section className={st(cl.sidebar__top)}>
        <Link to="/" style={{ width: "max-content" }}>
          <img src={logoPDP} alt="logo" className="img-fluid" />
        </Link>
        <Card onToggleOpen={onToggleOpen} />
      </section>
      <section className={st(cl.sidebar__list)}>
        <List onToggleOpen={onToggleOpen} />
      </section>
    </nav>
  );
};
