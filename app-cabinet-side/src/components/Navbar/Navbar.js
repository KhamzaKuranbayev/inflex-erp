import { Avatar } from "../Elements/Avatar/Avatar";
import { Icon } from "../Elements/Icon/Icon";
import cl from "./Navbar.module.scss";

export const Navbar = ({ onToggleOpen }) => {
  return (
    <>
      <nav className={cl.navbar__acc}>
        <section className={cl.navbar__left}>
          <div>
            <Icon icon="icon-menu" size="md" withHover onClick={onToggleOpen} />
          </div>
        </section>
        <section className={cl.navbar__right}>
          <Icon icon="icon-chat" size="md" withHover />
          <Icon icon="icon-notification" size="md" withHover />
          <Avatar circle />
        </section>
      </nav>
    </>
  );
};
