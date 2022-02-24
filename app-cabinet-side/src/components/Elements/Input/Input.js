import st from "classnames";
import { useState } from "react";
import { Icon } from "../Icon/Icon";
import "./Input.scss";

export const Input = ({
  type = "text",
  size = "md",
  label = null,
  color = "",
  error = null,
  success = null,
  ...rest
}) => {
  ////////////////////////////////////

  const [isShowPas, setIsShowPas] = useState(false);
  const [labelPos, setLabelPos] = useState(false);

  const toggleShowHide = () => setIsShowPas((p) => !p);

  const handlePosition = () => {
    if (document.getElementById(label).value === "") {
      setLabelPos(false);
    }
  };

  ///////////////////////////////////////

  return (
    <div className="inputWrapper">
      {label && (
        <label
          htmlFor={label}
          className={st("ui__label", {
            setPosition: labelPos,
            error,
            success,
            smallSize: size === "md",
          })}
        >
          {label}
        </label>
      )}

      <input
        id={label}
        type={isShowPas ? "text" : type}
        className={st("ui__input", {
          error,
          success,
          lg: size === "lg",
          md: size === "md",
          isPassword: type === "password",
        })}
        onFocus={() => label && setLabelPos((p) => true)}
        onBlur={() => label && handlePosition()}
        {...rest}
      />

      {type === "password" && (
        <div className="icon__eye__wrapper">
          <Icon icon="icon-eye-open" size="lg" onClick={toggleShowHide} />
        </div>
      )}

      {error && <p className="ui__error__text">{error}</p>}
    </div>
  );
};
