import sys
import os
import subprocess

def deploy_to_env(env_arg):
    cd_to_deploy_dir()
    return {
        'local-k8s': deploy_to_local_k8s,
        'dev': deploy_to_dev,
        'prod': deploy_to_prod
    }.get(env_arg, profile_help)

def deploy_to_local_k8s():
    print("Deploying to local k8s")

    rsync_local_to_remote = ["rsync", "-a", "--delete", "../../storage-service/", "matt@192.168.1.23:/home/matt/k8s/storage-service"]

    proc = subprocess.Popen(rsync_local_to_remote, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    out, err = proc.communicate() #outputs not used at the moment -- TODO add error handling

    push_image_on_remote = ["ssh", "matt@192.168.1.23", "/home/matt/k8s/storage-service/deploy/deploy-k8s.sh"]

    proc = subprocess.Popen(push_image_on_remote, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    out, err = proc.communicate() #outputs not used at the moment -- TODO add error handling


def deploy_to_dev():
    print("deploying to dev")


def deploy_to_prod():
    print("deploying to prod")


def profile_help():
    print("First argument must be local-k8s, dev, or prod")


def cd_to_deploy_dir():
    os.chdir(os.path.dirname(os.path.realpath(__file__))) #cd to this file's directory


if __name__ == "__main__":
    deploy_to_env(sys.argv[1])()
