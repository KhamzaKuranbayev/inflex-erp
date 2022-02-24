import cl from "./Card.module.scss";
import st from "classnames";
import { Avatar } from "../../../Elements/Avatar/Avatar";

export const Card = ({ onToggleOpen }) => {
  return (
    <div className={st(cl.user__card)} onClick={onToggleOpen}>
      <div className={st(cl.user__card__left)}>
        <Avatar circle sm />
      </div>
      <div className={st(cl.user__card__right)}>
        <h4 className={st(cl.user__name)}>Kamalov Bahrom </h4>
        <p className={st(cl.user__role)}>Student</p>
        <p className={st(cl.user__phone)}>(+998 97) 123 45 67</p>
      </div>
    </div>
  );
};
