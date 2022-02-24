import "./Icon.scss";
import st from "classnames";

export const Icon = ({ className, icon, size, withHover, color, ...rest }) => {
  return (
    <div
      className={st("iconWrapper", {
        xl: size === "xl",
        lg: size === "lg",
        md: size === "md",
        sm: size === "sm",
        withHover,
        bgGreen: color === "green",
        bgInfo: color === "info",
        bgSuccess: color === "success",
        bgWarning: color === "warning",
        bgDanger: color === "danger",
      })}
      {...rest}
    >
      <div
        className={st("icon", className, icon, {
          xl: size === "xl",
          lg: size === "lg",
          md: size === "md",
          sm: size === "sm",
          dark: color === "dark",
          green: color === "green",
          info: color === "info",
          success: color === "success",
          warning: color === "warning",
          danger: color === "danger",
          white: color === "white",
        })}
      />
    </div>
  );
};
