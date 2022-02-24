import st from "classnames";
import { Icon } from "../Icon/Icon";
import "./Button.scss";

export const Button = ({
  children,
  startIcon = null,
  endIcon = null,
  color = null,
  size = "md",
  variant,
  disabled = false,
  className,
  ...rest
}) => {
  return (
    <button
      className={st("ui__button", className, {
        lg: size === "lg",
        md: size === "md",
        sm: size === "sm",
        withIcon: startIcon || endIcon,
        defDark: color === "defDark",
        green: color === "green",
        darkGreen: color === "darkGreen",
        info: color === "info",
        primary: color === "primary",
        success: color === "success",
        darkSuccess: color === "darkSuccess",
        warning: color === "warning",
        darkWarning: color === "darkWarning",
        danger: color === "danger",
        darkDanger: color === "darkDanger",
        btnDisable: disabled,
        btnOutlined: variant === "outlined",
        btnText: variant === "text",
      })}
      disabled={disabled}
      {...rest}
    >
      {startIcon &&
        (disabled ? (
          <Icon icon={startIcon} />
        ) : (
          <Icon
            icon={startIcon}
            color={
              color === null ||
              color === "defDark" ||
              color === "success" ||
              color === "darkSuccess" ||
              color === "warning" ||
              color === "darkWarning"
                ? "dark"
                : "white"
            }
          />
        ))}
      {children}
      {endIcon &&
        (disabled ? (
          <Icon icon={endIcon} />
        ) : (
          <Icon
            icon={endIcon}
            color={
              color === null ||
              color === "defDark" ||
              color === "success" ||
              color === "darkSuccess" ||
              color === "warning" ||
              color === "darkWarning"
                ? "dark"
                : "white"
            }
          />
        ))}
    </button>
  );
};
