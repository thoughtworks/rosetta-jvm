import os.path
import os
import time
from boto.ec2.connection import EC2Connection

conn = EC2Connection(validate_certs=False)

ami = 'ami-9878c0f1'
instance_type = 't1.micro'
key_name = 'deployment-key'
key_output_directory = os.environ.get('KEY_OUTPUT_DIRECTORY') or '~/'
key_path = key_output_directory + key_name + '.pem'
group_name = 'rosetta-jvm'

if [key_pair.name for key_pair in conn.get_all_key_pairs()].count(key_name):
    conn.delete_key_pair(key_name)

if [security_group.name for security_group in conn.get_all_security_groups()].count(group_name):
    conn.delete_security_group(group_name)

if os.path.exists(key_path):
    os.rm(key_path)

deployment_key = conn.create_key_pair(key_name)

application_server = conn.create_security_group(group_name, 'Rosetta JVM Application Server')
application_server.authorize(ip_protocol='tcp', from_port=22, to_port=22, cidr_ip='0.0.0.0/0')

reservation = conn.run_instances(ami, instance_type=instance_type, key_name=deployment_key.name, security_groups=[application_server.name])
instance = reservation.instances[0]

deployment_key.save(key_output_directory)

print 'pending...'

while instance.state != 'running':
    instance.update()
    time.sleep(1)

print 'running...'
print 'connect with: "ssh -i ' + key_path + ' ubuntu@' + instance.public_dns_name + '"'
