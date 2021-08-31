# Chapter 1 Introduction

The internet: a "nuts and bolts" view
- Billions of connected computing devices
- hosts = end systems
- running network apps at Intenet's "edge

Packet switches: fowarding packets

Communication links (fiber, copper, radio, satellite)

Networks: Collection of devices, routes, and links

The Internet is a network of networks using Interconnected ISP's

Internet Protocols
- IP: Internet Protocol
- TCP: Transmission Control Protocol
- HTTP: Hypertext Transfer Protocol
- Eth: Ethernet
- Wi-Fi: Wireless

Internet standards
- RCF (Request for Comments)
- IETF (Internet Engineering Task Force)

*Infrastructure* that provides the basic services for the Internet, provides *programming interface* to distributed applications
- "hooks" allow seding and receiving packets

Access Networks have a transmittion rate in bits per second
- Example is a home network with a DSL or cable modem to ISP
- Connected to a router
- Connect wifi access points to router, or different devices

WLAN: Wireless Local Area Network
- 802.11b/g/n (Wi-Fi): 11, 54, 340 Mbps tranmission rates

Wide Area Network (WAN)
- provides access to the Internet foe mobile devices

Enterprise Network
- provides access to the Internet for business
- mix of wired, wireless links, switch, router, and firewall

Host: sends packets to the network
- breaks into smaller chunks known as packets of length L bits
- Transmits packets into access network at transmittion rate R
   - link transmition rate, aka link capacity, aka link bandwith

packet transmition delay = time needed to transmit a packet = $L/R$

packet switching: hosts break applications into packets
- forwards packets from one router to another

- Transmission delay: takes L/R seconds to transmit a packet
- Store and forward: entire packets must arrive at destination before it can transmit on next link
- End-to-end delay: 2L/R, assuming zero propagation delay

Local actions move arriving packets from router input link to output link

Routing global actions determine source destinations paths 

Packets queue in router buffer
- arrival rates to link (temporarily) exceed link capacity: packet loss

4 sources of packet delay, nodal processing, queueing delay, tranmission delay, and propagation delay

$d_t = L/R$ </br>
$d_p = d/s$

### Example
1. Consider sending a packet from a source host to a destination host over a fixed route. List the delay components in the end-to-end delay. Which of these delays are constant and which are variable? 
> Answer: Transmission delay is constant, queueing delay is variable, propagation delay is constant, and nodal processing delay is constant.

2. Suppose two hosts, A and B, are separated by 20,000 kilometers and are connected by a direct link of R  = 2 Mbps. Suppose the propagation speed over the link is 2.5 x 108 meters/sec. Consider sending a file of 800,000 bits from Host A to Host B. Suppose the file is sent continuously as one large message. What is the maximum number of bits that will be in the link at any given time?
> Answer: Propogated delay = 20000/2.5E5 = 0.08 seconds, bits in transit = 0.08 * 2*10^6 = 160,000 bits

Bottleneck link is the link on end-end path that constrains end-end throughput


,