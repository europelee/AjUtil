package org.upnp.alljoynservice.end;

public interface IBusDataListener
{
	public boolean RecvBusData(byte[] data);
	public boolean RecvBusData(String data);
}
