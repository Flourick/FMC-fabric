package me.flourick.fvt.settings;

import java.util.List;

import net.minecraft.client.option.Option;
import net.minecraft.text.OrderedText;

/**
 * Reimplementation of vanilla's Option.
 * 
 * @author Flourick
 */
public abstract class FVTOption<T> extends Option implements SaveableValue
{
	public FVTOption(String key)
	{
		super(key);
	}

	public abstract T getValueRaw();
	public abstract void setValueDefault();

	public abstract List<OrderedText> getTooltip();
}
