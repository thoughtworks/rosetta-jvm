from boto.ec2.connection import EC2Connection

conn = EC2Connection(validate_certs=False)

conn.run_instances('ami-eafa5883');